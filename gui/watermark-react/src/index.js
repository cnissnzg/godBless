import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import Base from './component/superbase'
import Home from './home.js'
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <Home />
  </React.StrictMode>,
  document.getElementById('root')
);


reportWebVitals();