/*
api接口配置
 */
const prefix = '';
const adminprefix = prefix+'/admin';
const Url = {


  homepage: prefix+'/',
  login: prefix+'/login',
  quickvisit: prefix+'/quickvisit',
  authcheck: prefix+'/authcheck',
  admindashboard : adminprefix,

  problem:{
    list: prefix+'/testset',
    detail: pid => prefix+"/testset/"+pid,
    submit: pid => prefix+"/testset/submit/"+pid,
    discuss: pid => prefix+"/problemset/"+pid+"/discuss",
    statistic: pid => prefix+"/problemset/"+pid+"/statistic",

    admin:{
      edit: pid => adminprefix+'/editproblem/'+pid,
      add: adminprefix+'/addproblem',
      list: adminprefix+'/problem',
      downloadtestdata: (pid,type) => adminprefix+"/downloadtestdata/"+pid+'/'+type,
    }
  },

  judge:{
    status: prefix+'/status',
  },

  hojuser:{
    detail: uid => prefix+"/user/"+uid,
    logout: prefix+"/logout",
    changepassword: prefix+"/changepassword",
    register : prefix+"/register",
    admin:{
      dashboard: adminprefix+'/user',
    }
  },

  contest:{
    list: prefix+'/contest',
    home: cid => prefix+'/contest/'+cid,
    ranklist: cid => prefix+'/contest/'+cid+"/ranklist",
    status: cid => prefix+'/contest/'+cid+"/status",
    mystatus: cid => prefix+'/contest/'+cid+"/mystatus",
    problem: (cid,innerid) => prefix+'/contest/'+cid+"/problem/"+innerid,
    submit: (cid,innerid) => prefix+'/contest/'+cid+"/problem/"+innerid+"/submit",
    discuss: (cid,innerid="General") => prefix+'/contest/'+cid+"/discuss/"+innerid,
    admin:{
      add: adminprefix+'/addcontest',
      edit: cid => adminprefix+'/editcontest/'+cid,
      list: adminprefix+'/contest',
      dashboard: cid => adminprefix+'/contest/'+cid,
      ranklist : cid => adminprefix+'/contest/'+cid+'/board',
      balloon : cid => adminprefix+'/contest/'+cid+'/balloon',
    }
  },


  group:{
    list: prefix+'/group',
    detail: gid => prefix+'/group/'+gid,
    join: gid => prefix+'/group/'+gid+'/join',
    ranklist: gid => prefix+'/group/'+gid+'/ranklist',

    admin:{
      add: adminprefix+'/addgroup',
      edit: gid => adminprefix+'/editgroup/'+gid,
      list: adminprefix+'/group',
      dashboard: gid => adminprefix+'/group/'+gid,
    }
  },

  blog:{
    list: prefix+'/article',
    detail: blogid => prefix+'/article/'+blogid,

    admin:{
      add: adminprefix+'/addblog',
      edit: blogid => adminprefix+'/editblog/'+blogid,
      list: adminprefix+'/blog',
    }
  },

};

export default Url;