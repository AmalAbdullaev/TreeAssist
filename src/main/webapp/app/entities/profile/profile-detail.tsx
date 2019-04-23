import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './profile.reducer';
import { IProfile } from 'app/shared/model/profile.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProfileDetail extends React.Component<IProfileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { profileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Profile [<b>{profileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fullName">Full Name</span>
            </dt>
            <dd>{profileEntity.fullName}</dd>
            <dt>
              <span id="birthday">Birthday</span>
            </dt>
            <dd>
              <TextFormat value={profileEntity.birthday} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="familyPhones">Family Phones</span>
            </dt>
            <dd>{profileEntity.familyPhones}</dd>
            <dt>
              <span id="bloodType">Blood Type</span>
            </dt>
            <dd>{profileEntity.bloodType}</dd>
            <dt>
              <span id="allergicReactions">Allergic Reactions</span>
            </dt>
            <dd>{profileEntity.allergicReactions}</dd>
            <dt>
              <span id="sex">Sex</span>
            </dt>
            <dd>{profileEntity.sex}</dd>
            <dt>
              <span id="phone">Phone</span>
            </dt>
            <dd>{profileEntity.phone}</dd>
            <dt>
              <span id="isVolunteer">Is Volunteer</span>
            </dt>
            <dd>{profileEntity.isVolunteer ? 'true' : 'false'}</dd>
            <dt>
              <span id="fcmToken">Fcm Token</span>
            </dt>
            <dd>{profileEntity.fcmToken}</dd>
            <dt>
              <span id="latitude">Latitude</span>
            </dt>
            <dd>{profileEntity.latitude}</dd>
            <dt>
              <span id="longitude">Longitude</span>
            </dt>
            <dd>{profileEntity.longitude}</dd>
            <dt>
              <span id="organization">Organization</span>
            </dt>
            <dd>{profileEntity.organization}</dd>
            <dt>
              <span id="login">Login</span>
            </dt>
            <dd>{profileEntity.login}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{profileEntity.email}</dd>
            <dt>User</dt>
            <dd>{profileEntity.userLogin ? profileEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/profile" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/profile/${profileEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ profile }: IRootState) => ({
  profileEntity: profile.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfileDetail);
