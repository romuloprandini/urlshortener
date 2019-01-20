import React, { Component } from 'react';
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  CardBody,
  CardHeader,
  ListGroup,
  ListGroupItem,
  Badge
} from 'reactstrap';

import urlService from './UrlService';

class Statistic extends Component {

  state={
    topUrls:[],
    topHosts:[],
  }

  componentDidMount = async () => {
    const topUrls = await urlService.topUrl();
    this.setState({topUrls});
    const topHosts = await urlService.topHost();
    this.setState({topHosts});
  }

  render = () => {
    return (
        <Container>
          <Row sm="12" md={{ size: 6, offset: 3 }}>
            <Card block>
              <CardBody>
                <CardHeader tag="h3">Top 10 Url</CardHeader>
                <ListGroup>
                  {this.state.topUrls.map(url => <ListGroupItem className="justify-content-between">{url.url}<Badge pill>{url.totalAccess}</Badge></ListGroupItem>)}
                </ListGroup>
              </CardBody>
            </Card>
          </Row>
          <Row sm="12" md={{ size: 6, offset: 3 }}>
            <Card block>
              <CardBody>
                <CardHeader tag="h3">Top 10 Hosts</CardHeader>
                <ListGroup>
                  {this.state.topHosts.map(host => <ListGroupItem className="justify-content-between">{host.host}<Badge pill>{host.count}</Badge></ListGroupItem>)}
                </ListGroup>
              </CardBody>
            </Card>
          </Row>
        </Container>
    );
  }
}
export default Statistic;
