import request from '@/utils/request';
import RequestUtils from '@/components/Dingkc/Common/RequestUtils';

export async function query() {
  return request('/api/users');
}
//
// export async function queryCurrent() {
//   return request('/api/currentUser');
// }

//查询当前用户
export async function queryCurrent(params) {
  return RequestUtils.requestGET('/users',params);
}

//登录
export async function fakeAccountLogin(params) {
  return RequestUtils.requestPOST('/login/account',params);
}


//注册
export async function fakeRegister(params) {
  return RequestUtils.requestPOST('/users',params);
}

//获取邮箱验证码
export async function getVerifyCodes(params) {
  return RequestUtils.requestPOST(`/verify-codes`, params);
}

//校验邮箱验证码
export async function checkVerifyCode(params) {
  return RequestUtils.requestPOST(`/verification-codes/validations`, params);
}

//重置密码
export async function resetPassword(params) {
  return RequestUtils.requestPUT(`/users/password-resetting`,null, params);
}

// 退出登录
export async function logoutCurrentUser() {
  return RequestUtils.requestGET('/users-exit');
}

//修改用户信息
export async function updateUser(params) {
  return RequestUtils.requestPUT(`/users/${params.userId}`,null,params);
}
