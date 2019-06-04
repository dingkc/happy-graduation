import React, { PureComponent } from 'react';

export default class UrlAnalysisUtils extends PureComponent {
  /**
   *URL参数转对象
   *?aaa=b&ccc=Z
   */
  static urlParamToObj(data) {
    var theRequest = new Object();
    if (data.startsWith('?')) {
      data = data.substr(1);
    }
    let dataArr = data.split('&');
    for (var i = 0; i < dataArr.length; i++) {
      theRequest[dataArr[i].split('=')[0]] = unescape(dataArr[i].split('=')[1]);
    }
    return theRequest;
  }
}
