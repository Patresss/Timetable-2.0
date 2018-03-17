import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Division} from './division.model';
import {ResponseWrapper} from '../../shared';
import {EntityService} from '../entity.service';

@Injectable()
export class DivisionService extends EntityService<Division> {

    constructor(http: Http) {
        super(http, 'divisions')
    }

    findByDivisionType(divisionType: String): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/type/` + divisionType)
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
