import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './profile.reducer';
import { IProfile } from 'app/shared/model/profile.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProfileUpdateState {
  isNew: boolean;
  userId: string;
}

export class ProfileUpdate extends React.Component<IProfileUpdateProps, IProfileUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { profileEntity } = this.props;
      const entity = {
        ...profileEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/profile');
  };

  render() {
    const { profileEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="treeassistApp.profile.home.createOrEditLabel">Create or edit a Profile</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : profileEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="profile-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fullNameLabel" for="fullName">
                    Full Name
                  </Label>
                  <AvField id="profile-fullName" type="text" name="fullName" />
                </AvGroup>
                <AvGroup>
                  <Label id="birthdayLabel" for="birthday">
                    Birthday
                  </Label>
                  <AvField id="profile-birthday" type="date" className="form-control" name="birthday" />
                </AvGroup>
                <AvGroup>
                  <Label id="familyPhonesLabel" for="familyPhones">
                    Family Phones
                  </Label>
                  <AvField id="profile-familyPhones" type="text" name="familyPhones" />
                </AvGroup>
                <AvGroup>
                  <Label id="bloodTypeLabel" for="bloodType">
                    Blood Type
                  </Label>
                  <AvField id="profile-bloodType" type="text" name="bloodType" />
                </AvGroup>
                <AvGroup>
                  <Label id="allergicReactionsLabel" for="allergicReactions">
                    Allergic Reactions
                  </Label>
                  <AvField id="profile-allergicReactions" type="text" name="allergicReactions" />
                </AvGroup>
                <AvGroup>
                  <Label id="sexLabel">Sex</Label>
                  <AvInput
                    id="profile-sex"
                    type="select"
                    className="form-control"
                    name="sex"
                    value={(!isNew && profileEntity.sex) || 'MALE'}
                  >
                    <option value="MALE">MALE</option>
                    <option value="FEMALE">FEMALE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    Phone
                  </Label>
                  <AvField id="profile-phone" type="text" name="phone" />
                </AvGroup>
                <AvGroup>
                  <Label id="isVolunteerLabel" check>
                    <AvInput id="profile-isVolunteer" type="checkbox" className="form-control" name="isVolunteer" />
                    Is Volunteer
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="fcmTokenLabel" for="fcmToken">
                    Fcm Token
                  </Label>
                  <AvField id="profile-fcmToken" type="text" name="fcmToken" />
                </AvGroup>
                <AvGroup>
                  <Label id="latitudeLabel" for="latitude">
                    Latitude
                  </Label>
                  <AvField id="profile-latitude" type="text" name="latitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="longitude">
                    Longitude
                  </Label>
                  <AvField id="profile-longitude" type="text" name="longitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="organizationLabel" for="organization">
                    Organization
                  </Label>
                  <AvField id="profile-organization" type="text" name="organization" />
                </AvGroup>
                <AvGroup>
                  <Label id="loginLabel" for="login">
                    Login
                  </Label>
                  <AvField id="profile-login" type="text" name="login" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    Email
                  </Label>
                  <AvField id="profile-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label for="user.login">User</Label>
                  <AvInput id="profile-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/profile" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  profileEntity: storeState.profile.entity,
  loading: storeState.profile.loading,
  updating: storeState.profile.updating,
  updateSuccess: storeState.profile.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfileUpdate);
