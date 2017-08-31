import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Lesson} from './lesson.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {createRequestOptionWithDivisionsId} from '../../shared/model/request-util';

@Injectable()
export class LessonService {

    private resourceUrl = 'api/lessons';
    private resourceByCurrentLoginUrl = 'api/lessons/login';
    private resourceByDivisionsIdUrl = 'api/lessons/divisions';

    constructor(private http: Http) {
    }

    create(lesson: Lesson): Observable<Lesson> {
        const copy = this.convert(lesson);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(lesson: Lesson): Observable<Lesson> {
        const copy = this.convert(lesson);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Lesson> {
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

    findByDivision(ids: number[], req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourceByDivisionsIdUrl, createRequestOptionWithDivisionsId(ids, req))
            .map((res: Response) => this.convertResponse(res));
    }

    findByCurrentLogin(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceByCurrentLoginUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(lesson: Lesson): Lesson {
        const copy: Lesson = Object.assign({}, lesson);
        return copy;
    }
}
