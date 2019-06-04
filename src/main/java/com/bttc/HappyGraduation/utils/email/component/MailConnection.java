package com.bttc.HappyGraduation.utils.email.component;

import com.bttc.HappyGraduation.utils.email.util.EmailValidator;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class MailConnection {
	private static final String EMAIL_NICK_PREFIX = "<";
	private static final String EMAIL_NICK_SUFFIX = ">";

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.nickname}")
	private String nickname;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailValidator emailValidator;

	/**
	 * 发送邮件接口
	 *
	 * @param toAddress
	 *            收件人地址
	 * @param ccList
	 *            抄送人地址
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param files
	 *            附件
	 * @return
	 * @throws BusinessException 
	 */
	public String sendMail(String toAddress, String ccList, String subject, String content, File[] files) throws BusinessException {
		if (StringUtils.isBlank(toAddress)) {
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "toAddress");
		}

		if (StringUtils.isBlank(subject)) {
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "subject");
		}

		try {
			// 创建一个邮件对象
			MimeMessage message = javaMailSender.createMimeMessage();

			// 使用Spring提供的MimeMessageHelper，简化java mail发送邮件的api
			MimeMessageHelper helper = null;

			if ((files != null) && (files.length > 0)) {
				helper = new MimeMessageHelper(message, true, "utf-8");
			} else {
				helper = new MimeMessageHelper(message, "utf-8");
			}

			try {
				// 设置发件人
				helper.setFrom(username, nickname);
			} catch (UnsupportedEncodingException e) {
				log.error("设置昵称失败!", e);
				helper.setFrom(username);
			}

			StringBuilder addressFail = new StringBuilder();

			// 设置收件人
			// 过滤因前台操作造成的空邮箱
			String[] address = toAddress.split(",");
			List<InternetAddress> addresses = new ArrayList<>();
			// 去除所有不合法的邮件
			structureEmails(address, addresses, addressFail);

			if (CollectionUtils.isEmpty(addresses)) {
				BusinessException.throwBusinessException(ErrorCode.EMAIL_NOT_LEGAL);
			}

			helper.setTo(addresses.toArray(
					new InternetAddress[addresses.size()]));

			// 设置抄送人
			if (StringUtils.isNotBlank(ccList)) {
				String[] ccAddress = ccList.split(",");

				List<InternetAddress> ccAddresses = new ArrayList<>();

				structureEmails(ccAddress, ccAddresses, addressFail);

				if (!CollectionUtils.isEmpty(addresses)) {
					helper.setCc(ccAddresses.toArray(
							new InternetAddress[ccAddresses.size()]));
				}
			}

			// 设置主题
			helper.setSubject(subject);
			// 设置内容
			helper.setText(content, true);

			// 设置上传附件(如果存在的话)
			if (files != null) {
				for (File file : files) {
					if (file != null) {
						helper.addAttachment(file.getName(), file);
					}
				}
			}

			// 发送邮件
			javaMailSender.send(message);

			// 处理返回值
			String msg = "";

			if ((addressFail != null) && (addressFail.length() > 0)) {
				msg = addressFail.substring(0, addressFail.length() - 1) +
						"路径有误，其余已发送成功！";
			} else {
				msg = "发送成功！";
			}

			return msg;
		} catch (Exception ex) {
			log.error("邮件发送失败!", ex);

			String errorInfo = null;

			if (ex instanceof MailSendException) {
				MailSendException e = (MailSendException) ex;

				Map<Object, Exception> emap = e.getFailedMessages();

				for (Object obj : emap.keySet()) {
					Exception one = emap.get(obj);

					if (one instanceof SendFailedException) {
						SendFailedException sendEX = (SendFailedException) one;

						Address[] failsentAddresses = sendEX.getInvalidAddresses();

						StringBuilder sb = new StringBuilder();

						if ((failsentAddresses != null) &&
								(failsentAddresses.length > 0)) {
							sb.append("失效的邮件地址 : ");

							for (Address a : failsentAddresses) {
								sb.append(a).append(",");
							}

							errorInfo = sb.substring(0, sb.length() - 1);
						}
					}
				}
			}

			BusinessException.throwBusinessException(ErrorCode.EMAIL_FAIL,
					errorInfo);

			return null;
		}
	}

	private void structureEmails(String[] address,
								 List<InternetAddress> addresses, StringBuilder addressFail) {
		if ((address == null) || (address.length == 0)) {
			return;
		}

		for (int i = 0; i < address.length; i++) {
			if (StringUtils.isBlank(address[i])) {
				addressFail.append(address[i]).append(",");

				continue;
			}

			InternetAddress addr = new InternetAddress();

			String one = address[i].trim();

			int prefix = StringUtils.indexOf(one, EMAIL_NICK_PREFIX);
			int suffix = StringUtils.indexOf(one, EMAIL_NICK_SUFFIX);

			String nick = null;
			String oneAddress = null;

			if ((prefix > 0) && (suffix > 0) && (suffix > prefix)) {
				nick = StringUtils.substring(one, 0, prefix);
				oneAddress = StringUtils.substring(one, prefix + 1, suffix)
						.trim();
			} else {
				oneAddress = one.trim();
			}

			if (StringUtils.isNotBlank(oneAddress)) {
				if (emailValidator.isValid(oneAddress)) {
					addr.setAddress(oneAddress);

					try {
						addr.setPersonal(nick);
					} catch (UnsupportedEncodingException e) {
					}

					addresses.add(addr);
				} else {
					addressFail.append(one).append(",");
				}
			} else {
				addressFail.append(one).append(",");
			}
		}
	}
}
