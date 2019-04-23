import { Moment } from 'moment';

export const enum HumanSex {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export interface IProfile {
  id?: number;
  fullName?: string;
  birthday?: Moment;
  familyPhones?: string;
  bloodType?: string;
  allergicReactions?: string;
  sex?: HumanSex;
  phone?: string;
  isVolunteer?: boolean;
  fcmToken?: string;
  latitude?: string;
  longitude?: string;
  organization?: string;
  login?: string;
  email?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IProfile> = {
  isVolunteer: false
};
