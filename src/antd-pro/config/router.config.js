export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', name: 'login', component: './User/Login' },
      { path: '/user/register', name: 'register', component: './User/Register' },
      {
        path: '/user/forgetPassword', component: './User/ForgetPassword',
      },
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    routes: [
      { path: '/', redirect: '/file' },
      {
        name: '文件',
        path: '/file',
        component: './File/FileLayout',
        // routes: [
        //   {
        //     path: '/file/all-file',
        //     name: '全部文件',
        //     component: './File/AllFiles'
        //   },
        //   {
        //     path: '/file/document',
        //     name: '文档',
        //   },
        //   {
        //     path: '/file/picture',
        //     name: '图片'
        //   },
        //   {
        //     path: '/file/vedio',
        //     name: '视频'
        //   },
        //   {
        //     path: '/file/recycle',
        //     name: '回收站'
        //   },
        //   {
        //     path: '/file/share',
        //     name: '分享'
        //   }
        // ],
      },
      {
        path: '/file/:ftpFileId/fileDetail',
        component: './File/FileDetail',
      },
      {
        name: '记事',
        path: '/notes',
        component: './Note/NoteLayout'
      },
      // list
      // {
      //   path: '/list',
      //   icon: 'table',
      //   name: 'list',
      //   routes: [
      //     {
      //       path: '/list/table-list',
      //       name: 'searchtable',
      //       component: './List/TableList',
      //     },
      //     {
      //       path: '/list/basic-list',
      //       name: 'basiclist',
      //       component: './List/BasicList',
      //     },
      //     {
      //       path: '/list/card-list',
      //       name: 'cardlist',
      //       component: './List/CardList',
      //     },
      //     {
      //       path: '/list/search',
      //       name: 'searchlist',
      //       component: './List/List',
      //       routes: [
      //         {
      //           path: '/list/search',
      //           redirect: '/list/search/articles',
      //         },
      //         {
      //           path: '/list/search/articles',
      //           name: 'articles',
      //           component: './List/Articles',
      //         },
      //         {
      //           path: '/list/search/projects',
      //           name: 'projects',
      //           component: './List/Projects',
      //         },
      //         {
      //           path: '/list/search/applications',
      //           name: 'applications',
      //           component: './List/Applications',
      //         },
      //       ],
      //     },
      //   ],
      // },
      // {
      //   path: '/profile',
      //   name: 'profile',
      //   icon: 'profile',
      //   routes: [
      //     // profile
      //     {
      //       path: '/profile/basic',
      //       name: 'basic',
      //       component: './Profile/BasicProfile',
      //     },
      //     {
      //       path: '/profile/basic/:id',
      //       name: 'basic',
      //       hideInMenu: true,
      //       component: './Profile/BasicProfile',
      //     },
      //     {
      //       path: '/profile/advanced',
      //       name: 'advanced',
      //       authority: ['admin'],
      //       component: './Profile/AdvancedProfile',
      //     },
      //   ],
      // },
      // {
      //   name: 'result',
      //   icon: 'check-circle-o',
      //   path: '/result',
      //   routes: [
      //     // result
      //     {
      //       path: '/result/success',
      //       name: 'success',
      //       component: './Result/Success',
      //     },
      //     { path: '/result/fail', name: 'fail', component: './Result/Error' },
      //   ],
      // },
      // {
      //   name: 'exception',
      //   icon: 'warning',
      //   path: '/exception',
      //   routes: [
      //     // exception
      //     {
      //       path: '/exception/403',
      //       name: 'not-permission',
      //       component: './Exception/403',
      //     },
      //     {
      //       path: '/exception/404',
      //       name: 'not-find',
      //       component: './Exception/404',
      //     },
      //     {
      //       path: '/exception/500',
      //       name: 'server-error',
      //       component: './Exception/500',
      //     },
      //     {
      //       path: '/exception/trigger',
      //       name: 'trigger',
      //       hideInMenu: true,
      //       component: './Exception/TriggerException',
      //     },
      //   ],
      // },
      {
        name: '个人',
        path: '/account',
        // routes: [
        //   {
        //     path: '/account/settings',
        //     name: 'settings',
            component: './Account/Settings/SettingLayout',
          //   routes: [
          //     {
          //       path: '/account/settings',
          //       redirect: '/account/settings/base',
          //     },
          //     {
          //       path: '/account/settings/base',
          //       component: './Account/Settings/BaseView',
          //     },
          //     {
          //       path: '/account/settings/security',
          //       component: './Account/Settings/SecurityView',
          //     },
          //     {
          //       path: '/account/settings/binding',
          //       component: './Account/Settings/BindingView',
          //     },
          //     {
          //       path: '/account/settings/notification',
          //       component: './Account/Settings/NotificationView',
          //     },
          //   ],
          // },
      //   ],
      },
      {
        name: '分享',
        path: '/share',
        component: './Share/Share',
      },
      {
        component: '404',
      },
    ],
  },
];
