import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Teacher} from './teacher.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {createRequestOptionWithDivisionsId} from "../../shared/model/request-util";

@Injectable()
export class TeacherService {

    private resourceUrl = 'api/teachers';
    private resourceByCurrentLoginUrl = 'api/teachers/login';
    private resourceByDivisionsIdUrl = 'api/teachers/divisions';

    constructor(private http: Http) {
    }

    create(teacher: Teacher): Observable<Teacher> {
        const copy = this.convert(teacher);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(teacher: Teacher): Observable<Teacher> {
        const copy = this.convert(teacher);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Teacher> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByDivision(ids: number[], req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourceByDivisionsIdUrl, createRequestOptionWithDivisionsId(ids, req))
            .map((res: Response) => this.convertResponse(res));
    }

    findByCurrentLogin(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceByCurrentLoginUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(teacher: Teacher): Teacher {
        const copy: Teacher = Object.assign({}, teacher);
        return copy;
    }
}
