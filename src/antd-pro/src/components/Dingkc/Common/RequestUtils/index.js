/* eslint-disable linebreak-style */

import React, { PureComponent } from 'react';
import { stringify } from 'qs';
import request from '@/utils/request';

/**
 *系统各类请求公共函数的实现
 */
const requestUrl = '/api/v1';

const requestMethod = (url, method, paramsUrl, paramsBody) => {
  if (method === 'GET') {
    return request(`${requestUrl}${url}?${stringify(paramsUrl)}`);
  } else if (method === 'PUT') {
    return request(`${requestUrl}${url}?${stringify(paramsUrl)}`, {
      mode: 'cors',
      method,
      body: {
        ...paramsBody,
      },
    });
  } else if (method === 'DELETE') {
    return request(`${requestUrl}${url}?${stringify(paramsUrl)}`, {
      mode: 'cors',
      method,
      body: {
        ...paramsBody,
      },
    });
  } else {
    return request(`${requestUrl}${url}`, {
      mode: 'cors',
      method,
      body: {
        ...paramsBody,
      },
    });
  }
};

export default class RequestUtils extends PureComponent {
  static requestPath = `${location.origin}${requestUrl}`;
  /**
   *Get请求
   *
   */
  static requestGET = (url, params) => {
    console.log('get url===========', url);
    return requestMethod(url, 'GET', params, null);
  };
  /**
   *Post请求
   */
  static requestPOST = (url, params) => {
    console.log('post url===========', url);
    console.log('post params===========', params);
    return requestMethod(url, 'POST', null, params);
  };
  /**
   *Put请求
   */
  static requestPUT = (url, paramsURL, paramsBody) => {
    console.log('put url===========', url);
    console.log('put paramsURL===========', paramsURL);
    console.log('put paramsBody===========', paramsBody);
    return requestMethod(url, 'PUT', paramsURL, paramsBody);
  };
  /**
   *Delete请求
   */
  static requestDELETE = (url, params,paramsBody) => {
    console.log('del url============', url);
    return requestMethod(url, 'DELETE', params, paramsBody);
  };
}
