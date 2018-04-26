import {BaseEntity} from '../shared';

export class PreferenceDivisionByPlaceModel implements BaseEntity {

    constructor(
        public id?: number,
        public divisionId?: number,
        public placeId?: number,
        public points?: number,
        public divisionName = '',
        public placeName = '') {
    }

}
