import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
} from 'reactstrap';

import Shortener from './Shortener';
import Statistic from './Statistic';
import About from './About';
import Redirect from './Redirect';

class App extends Component {
  state = {
    isOpen: false,
  }

  toggle = () => {
    this.setState((prevState) => ({
      isOpen: !prevState.isOpen
    }));
  }

  render() {
    return (
      <Router>
      <div>
        <Navbar color="light" light expand="md">
          <NavbarBrand href="/">Shortener</NavbarBrand>
          <NavbarToggler onClick={this.toggle} />
          <Collapse isOpen={this.state.isOpen} navbar>
            <Nav className="ml-auto" navbar>
              <NavItem>
                <NavLink tag={Link}  to="/statistic">Statistic</NavLink>
              </NavItem>
              <NavItem>
                <NavLink tag={Link}  to="/about">About</NavLink>
              </NavItem>
              <NavItem>
                <NavLink href="https://github.com/romuloprandini/shortener">GitHub</NavLink>
              </NavItem>
            </Nav>
          </Collapse>
        </Navbar>
        <br />
        <Switch>
          <Route path="/" exact component={Shortener} />
          <Route path="/about" exact component={About} />
          <Route path="/statistic" exact component={Statistic} />
          <Route path="/:identifier" component={({match}) => <Redirect url={match.params.identifier} />} />
        </Switch>
      </div>
      </Router>
    );
  }
}

export default App;
