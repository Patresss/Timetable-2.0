import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Property} from './property.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class PropertyService {

    private resourceUrl = 'api/properties';
    private resourceByCurrentLoginUrl = 'api/properties/login';

    constructor(private http: Http) {
    }

    create(property: Property): Observable<Property> {
        const copy = this.convert(property);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(property: Property): Observable<Property> {
        const copy = this.convert(property);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Property> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByCurrentLogin(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceByCurrentLoginUrl, options)
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

    private convert(property: Property): Property {
        const copy: Property = Object.assign({}, property);
        return copy;
    }
}
