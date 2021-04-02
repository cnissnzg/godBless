import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {Url} from './common/common';
import Home from './home';
import ProblemList from './component/problem/home'

ReactDOM.render((
  <Router>
    <Switch>
      <Route exact path={Url.homepage} component={Home}/>
      <Route exact path={Url.problem.list} component={ProblemList}/>
    </Switch>
  </Router>
),
  document.getElementById('root')
);

