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

import urlService from './UrlService';
import { BASE_URL } from './Constants'

class Shortener extends Component {
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

  handleChange = (e) => {
    this.setState({
      [e.target.name] : e.target.value
    })
  }

  shortenerUrl = async (e) => {
    if (this.state.url !== "") {
      const shortedUrl = await urlService.create(this.state.url);
      this.setState({shortedUrl});
    }
  }

  render() {
    return (
        <Container>
          <Row sm="12" md={{ size: 6, offset: 3 }}>
            <Col>
              <Jumbotron>
                <h2 className="display-3 text-center">Url Shortener</h2>
                <p className="lead  text-center">Why lose time typing long URL when you can short it anythime you want?</p>
                <Form>
                  <Row>
                    <Col sm={10} md={10}>
                      <FormGroup>
                        <Label for="exampleEmail" sm={2} hidden>Url</Label>
                        <Input type="url" name="url" id="url" placeholder="http://vanhack.com" bsSize="lg" value={this.state.url} onChange={this.handleChange} />
                      </FormGroup>
                    </Col>
                    <Col sm={2} md={2}>
                      <Button color="primary" block size="lg" onClick={this.shortenerUrl}>Short</Button>
                    </Col>
                  </Row>
                </Form>
              </Jumbotron>
            </Col>
          </Row>

          { this.state.shortedUrl ? 
          <Row sm="12" md={{ size: 6, offset: 3 }}>
            <Col>
              <Jumbotron>
                <p className="lead  text-center">Here is your new, beautiful and nice short URL</p>
                <p className="lead  text-center">
                  <a className="text-center" style={{fontSize:"x-large"}} href={`${this.state.shortedUrl}`}>{BASE_URL+this.state.shortedUrl}</a>
                </p>
              </Jumbotron>
            </Col>
          </Row>
          : null }
        </Container>
    );
  }
}
export default Shortener;
