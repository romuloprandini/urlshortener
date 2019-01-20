import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
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
import Redirect from './Redirect';

class App extends Component {
  state = {
    isOpen: false,
    url: "",
    shortedUrl: null
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
                <Link to="/about">About</Link>
              </NavItem>
              <NavItem>
                <NavLink href="https://github.com/romuloprandini/shortener">GitHub</NavLink>
              </NavItem>
            </Nav>
          </Collapse>
        </Navbar>
        <br />
        <Route path="/" exact component={Shortener} />
    <Route path="/:identifier" component={({match}) => <Redirect url={match.params.identifier} />} />
      </div>
      </Router>
    );
  }
}

export default App;
