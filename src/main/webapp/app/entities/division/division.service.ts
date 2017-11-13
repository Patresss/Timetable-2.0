import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Division } from './division.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DivisionService {

    private resourceUrl = SERVER_API_URL + 'api/divisions';

    constructor(private http: Http) { }

    create(division: Division): Observable<Division> {
        const copy = this.convert(division);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(division: Division): Observable<Division> {
        const copy = this.convert(division);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Division> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByDivisionType(divisionType: String): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/type/` + divisionType)
            .map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(division: Division): Division {
        const copy: Division = Object.assign({}, division);
        return copy;
    }
}
