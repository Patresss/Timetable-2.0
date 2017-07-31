import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Properties } from './properties.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PropertiesService {

    private resourceUrl = 'api/properties';

    constructor(private http: Http) { }

    create(properties: Properties): Observable<Properties> {
        const copy = this.convert(properties);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(properties: Properties): Observable<Properties> {
        const copy = this.convert(properties);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Properties> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
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

    private convert(properties: Properties): Properties {
        const copy: Properties = Object.assign({}, properties);
        return copy;
    }
}
