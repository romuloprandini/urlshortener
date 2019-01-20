import React, { Component } from 'react';
import {
  Container,
  Row,
  Col,
  Jumbotron,
  Button,
  Form,
  FormGroup,
  Label,
  Input
} from 'reactstrap';

class About extends Component {
  render() {
    return (
        <Container>
          <Row sm="12" md={{ size: 6, offset: 3 }}>
            <Col>
              <Jumbotron>
                <h2 className="display-3 text-center">About</h2>
                <p className="lead  text-center">This application is part of Vanhack challenge, the main point is to prove my abilities in Java, React and Docker</p>
                <p className="lead  text-center">I dedicated hard to delivery a functional, maintanable and near production application</p>
                <p>You can help me improve my skill suggesting changes and improvements, just make a merge request at my <a href="https://github.com/romuloprandini/urlshortener">Github</a></p>
              </Jumbotron>
            </Col>
          </Row>
        </Container>
    );
  }
}
export default About;
