import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Place} from './place.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class PlaceService extends DivisionOwnerEntityService<Place> {

    constructor(http: Http) {
        super(http, 'places')
    }
}
