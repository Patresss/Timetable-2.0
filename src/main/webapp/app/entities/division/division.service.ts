import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Division} from './division.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {EntityService} from '../entity.service';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class DivisionService extends DivisionOwnerEntityService<Division> {

    constructor(http: Http) {
        super(http, 'divisions')
    }

    findByDivisionType(divisionType: String, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/type/` + divisionType, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findClassesByParentId(parentId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/parent/class/` + parentId)
            .map((res: Response) => this.convertResponse(res));
    }

    findSubgroupsByParentId(parentId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/parent/subgroup/` + parentId)
            .map((res: Response) => this.convertResponse(res));
    }

}
