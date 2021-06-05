import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
//import { HashRouter as Router, Switch, Route} from 'react-router-dom';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {Url} from './common/common';
import Home from './home';
import ProblemList from './component/problem/home';
import ProblemDetail from './component/problem/detail';
import ProblemSubmit from './component/problem/submit';
import AddProblem from './component/problem/addProblem';
import Register from './component/user/register';
import Login from './component/login';
import CodeEitor from './component/code/editor';
import UpdateCode from './component/code/updateCode';
import CodeList from './component/code/codeList';
import MyCodeList from './component/code/myCodeList';
import JudgeQueue from './component/judge/judgeQueue';
import RandomWatch from './component/watch/random';

ReactDOM.render((
  <Router>
    <Switch>
      <Route exact path={Url.homepage} component={Home}/>
      <Route exact path={Url.problem.list} component={ProblemList}/>
      <Route exact path={ Url.problem.detail(":pid") } component={ProblemDetail} />
      <Route path={Url.problem.submit(":pid") } component={ProblemSubmit} />
      <Route exact path={Url.user.register} component={Register} />
      <Route exact path={Url.login} component={Login} />
      <Route exact path={Url.code.editor(":algorithm")} component={CodeEitor} />
      <Route path={Url.code.updateCode } component={UpdateCode} />
      <Route path={Url.code.codeList} component={CodeList} />
      <Route path={Url.code.myCodeList} component={MyCodeList} />
      <Route exact path={Url.problem.add} component={AddProblem} />
      <Route exact path={Url.judge.status} component={JudgeQueue} />
      <Route exact path={Url.watch.random} component={RandomWatch} />
    </Switch>
  </Router>
),
  document.getElementById('root')
);

