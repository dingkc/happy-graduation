import React from 'react';
import { Router as DefaultRouter, Route, Switch } from 'react-router-dom';
import dynamic from 'umi/dynamic';
import renderRoutes from 'umi/_renderRoutes';
import RendererWrapper0 from 'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/.umi/LocaleWrapper.jsx'
import _dvaDynamic from 'dva/dynamic'

let Router = require('dva/router').routerRedux.ConnectedRouter;

let routes = [
  {
    "path": "/user",
    "component": _dvaDynamic({
  
  component: () => import(/* webpackChunkName: "layouts__UserLayout" */'../../layouts/UserLayout'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
    "routes": [
      {
        "path": "/user",
        "redirect": "/user/login",
        "exact": true
      },
      {
        "path": "/user/login",
        "name": "login",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__User__models__forgetPassword.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/forgetPassword.js').then(m => { return { namespace: 'forgetPassword',...m.default}}),
  import(/* webpackChunkName: 'p__User__models__register.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/register.js').then(m => { return { namespace: 'register',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__User__Login" */'../User/Login'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "path": "/user/register",
        "name": "register",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__User__models__forgetPassword.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/forgetPassword.js').then(m => { return { namespace: 'forgetPassword',...m.default}}),
  import(/* webpackChunkName: 'p__User__models__register.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/register.js').then(m => { return { namespace: 'register',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__User__Register" */'../User/Register'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "path": "/user/forgetPassword",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__User__models__forgetPassword.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/forgetPassword.js').then(m => { return { namespace: 'forgetPassword',...m.default}}),
  import(/* webpackChunkName: 'p__User__models__register.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/User/models/register.js').then(m => { return { namespace: 'register',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__User__ForgetPassword" */'../User/ForgetPassword'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "component": () => React.createElement(require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/node_modules/_umi-build-dev@1.8.0@umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "path": "/",
    "component": _dvaDynamic({
  
  component: () => import(/* webpackChunkName: "layouts__BasicLayout" */'../../layouts/BasicLayout'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
    "Routes": [require('../Authorized').default],
    "routes": [
      {
        "path": "/",
        "redirect": "/file",
        "exact": true
      },
      {
        "name": "文件",
        "path": "/file",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__File__models__file.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/File/models/file.js').then(m => { return { namespace: 'file',...m.default}}),
  import(/* webpackChunkName: 'p__File__models__recycle.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/File/models/recycle.js').then(m => { return { namespace: 'recycle',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__File__FileLayout" */'../File/FileLayout'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "name": "记事",
        "path": "/notes",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__Note__models__note.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/Note/models/note.js').then(m => { return { namespace: 'note',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__Note__NoteLayout" */'../Note/NoteLayout'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "name": "个人",
        "path": "/account",
        "component": _dvaDynamic({
  app: window.g_app,
models: () => [
  import(/* webpackChunkName: 'p__Account__Settings__models__geographic.js' */'D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/pages/Account/Settings/models/geographic.js').then(m => { return { namespace: 'geographic',...m.default}})
],
  component: () => import(/* webpackChunkName: "p__Account__Settings__SettingLayout" */'../Account/Settings/SettingLayout'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "component": _dvaDynamic({
  
  component: () => import(/* webpackChunkName: "p__404" */'../404'),
  LoadingComponent: require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/src/components/PageLoading/index').default,
}),
        "exact": true
      },
      {
        "component": () => React.createElement(require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/node_modules/_umi-build-dev@1.8.0@umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "component": () => React.createElement(require('D:/IdeaProjects/happy-graduation/src/ant-design-pro/node_modules/_umi-build-dev@1.8.0@umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
  }
];
window.g_routes = routes;
window.g_plugins.applyForEach('patchRoutes', { initialValue: routes });

// route change handler
function routeChangeHandler(location, action) {
  window.g_plugins.applyForEach('onRouteChange', {
    initialValue: {
      routes,
      location,
      action,
    },
  });
}
window.g_history.listen(routeChangeHandler);
routeChangeHandler(window.g_history.location);

export default function RouterWrapper() {
  return (
<RendererWrapper0>
          <Router history={window.g_history}>
      { renderRoutes(routes, {}) }
    </Router>
        </RendererWrapper0>
  );
}
