import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {Url} from './common/common';
import Home from './home';
import ProblemList from './component/problem/home';
import ProblemDetail from './component/problem/detail';
import ProblemSubmit from './component/problem/submit';
import Register from './component/user/register';
import Login from './component/login';
ReactDOM.render((
  <Router>
    <Switch>
      <Route exact path={Url.homepage} component={Home}/>
      <Route exact path={Url.problem.list} component={ProblemList}/>
      <Route exact path={ Url.problem.detail(":pid") } component={ProblemDetail} />
      <Route path={Url.problem.submit(":pid") } component={ProblemSubmit} />
      <Route exact path={Url.user.register} component={Register} />
      <Route exact path={Url.login} component={Login} />
    </Switch>
  </Router>
),
  document.getElementById('root')
);

