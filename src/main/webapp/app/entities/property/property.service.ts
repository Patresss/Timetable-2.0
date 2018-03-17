import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Property} from './property.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class PropertyService extends DivisionOwnerEntityService<Property> {

    constructor(http: Http) {
        super(http, 'properties')
    }

}
