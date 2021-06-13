/*
api接口配置
 */
//本地打包
//const host = 'http://39.105.21.114:12306/api/v1/watermark';
//浏览器
const host = '/api/v1/watermark';
const whole = 'http://39.105.21.114:12306/api/v1/watermark';
const Api = {
  login: host + '/user/login',


  admin:{
    uploadpic: host + '/api/v1/admin/upload/pic/',
  },
  problem : {
    list: (start,end,admin=false) => host + '/problem/get?start='+start+"&end="+end+"&admin="+admin,
    getOne: (pid) => host + '/problem/getOne?pid='+pid,
    getMaterial: (pid) => host + '/problem/getMaterial?pid='+pid,
    getChallenge: (pid) => host + '/problem/getChallenge?pid='+pid,


    count: host + '/api/v1/problem/count/',
    detail: pid => host + '/api/v1/problem/detail/?pid='+pid,
    info: pid => host + '/api/v1/problem/info/?pid='+pid,
    discussion: pid => host + '/api/v1/problem/discussion/?pid='+pid,
    newdiscuss: host + '/api/v1/problem/newdiscuss/',

    uploadtestdata: host + '/api/v1/problem/upload/testdata/',
    downloadtestdata: (pid,type,token) => host + '/api/v1/problem/download/testdata/?pid='+pid+'&type='+type+"&jwt="+token,
    checkDataExist: (pid) => host + '/api/v1/problem/checkDataExist/?pid='+pid,
    edit: host + '/api/v1/problem/edit/',

    uploadMaterial: host +  '/problem/upload',
    getParam: host + '/problem/getParam',
    add: host + '/problem/add',
  },

  judge : {
    status: host + '/api/v1/judge/status/',
    ceinfo: id => host + '/api/v1/judge/ceinfo/?id='+id,
    code: (id,cid=0) => host + '/api/v1/judge/code/?id='+id+'&cid='+cid,
    statistic: pid => host + '/api/v1/judge/statistic/?pid='+pid,
    submit: host + '/api/v1/judge/submit/',
    rejudge: host + '/api/v1/judge/rejudge/',
    reject: host + '/api/v1/judge/reject/',
    downloadcode: (cid,token) => host + '/api/v1/judge/downloadcode/?cid='+cid+'&jwt='+token,

    list: host + '/judge/list/',
    report: runId => host + '/judge/report?runId='+runId,
    start: (algorithmId,uid,pid) => host + '/judge/godBless?algorithmId='+algorithmId+'&uid='+uid+'&pid='+pid,
    compile: runId => host + '/judge/compile?runId='+runId,
    run: runId => host + '/judge/run?runId='+runId,
  },

  contest : {
    list: host + '/api/v1/contest/list/',
    info: cid => host + '/api/v1/contest/info/?cid='+cid,
    detail: (cid,admin=false) => host + '/api/v1/contest/detail/?cid='+cid+'&admin='+admin,
    problem: (cid,innerid) => host + '/api/v1/contest/problem/?cid='+cid+'&innerid='+innerid,
    ranklist: (cid,extra,ended) =>
      host + '/api/v1/contest/ranklist/?cid='+cid+'&extra='+extra+'&ended='+ended,
    discussion: cid => host + '/api/v1/contest/discussion/?cid='+cid,
    newdiscuss: host + '/api/v1/contest/newdiscuss/',
    exportranklist: (cid,extra,ended,token) =>
      host + '/api/v1/contest/exportranklist/?cid='+cid+'&extra='+extra+'&ended='+ended+'&jwt='+token,


    edit: host + '/api/v1/contest/edit/',
    closeboard: host + '/api/v1/contest/closeboard/',
    monocontest: host + '/api/v1/contest/monocontest/',
    getballoonlist:(cid)=> host + '/api/v1/contest/getballoonlist/?cid='+cid,
    balloonok: host + '/api/v1/contest/balloonok/',
  },

  user : {
    grouplist: host + '/api/v1/user/grouplist/',
    groupdetail: (gid,admin=false) => host + '/api/v1/user/groupdetail/?gid='+gid+'&admin='+admin,
    groupranklist: gid => host + '/api/v1/user/groupranklist/?gid='+gid,
    joingroup: host + '/api/v1/user/joingroup/',
    profile: uid => host +'/api/v1/user/detail/?uid='+uid,
    editgroup: host + '/api/v1/user/editgroup/',
    getgroupsecret : gid => host + '/api/v1/user/getgroupsecret/?gid='+gid,
    getgroupmd5 : gid => host + '/api/v1/user/getgroupmd5/?gid='+gid,
    changepassword : host + '/api/v1/user/changepassword/',
    register : host + '/user/register/',
    groupauth : host + '/api/v1/user/groupauth/',
    getseatlist : (cid)=>host + '/api/v1/user/getseatlist/?cid='+cid,
    addseat : host + '/api/v1/user/addseat/',
    cleanseat : host + '/api/v1/user/cleanseat/',
    cleanrating : host + '/api/v1/user/cleanrating/',
    updaterating : host + '/api/v1/user/updaterating/',
    downloadrating : (gid,token) => host + '/api/v1/user/downloadrating/?gid='+gid+'&jwt='+token,
    getstarlist : (cid)=>host + '/api/v1/user/getstarlist/?cid='+cid,
    addstar : host + '/api/v1/user/addstar/',
    cleanstar : host + '/api/v1/user/cleanstar/',
    getCaptcha : host + '/user/captcha/',
  },

  blog : {
    get: blogid => host + '/api/v1/blog/get/?blogid='+blogid,
    list: host + '/api/v1/blog/list/',
    edit: host + '/api/v1/blog/edit/',
  },

  code : {
    submit: host + '/algorithm/submit',
    list: (start,end) => host + '/algorithm/getAll?start='+start+"&end="+end,
    myList: (start,end,uid) => host + '/algorithm/getAll?start='+start+"&end="+end+"&uid"+uid,
    del : algorithmId => host + '/algorithm/del?algorithmId='+algorithmId,
    getFile: (name,uid) =>  whole + '/algorithm/getFile?name='+name+"&uid="+uid,
  }
};

export default Api;
