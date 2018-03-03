import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {CurriculumService} from '../curriculum';
import {createRequestOptionWithDivisionsId} from '../../shared';
import {CurriculumList} from './curriculum-list.model';

@Injectable()
export class CurriculumListService {

    private resourceUrl = SERVER_API_URL + 'api/curriculum-listes';
    private resourceByCurrentLoginUrl = SERVER_API_URL + 'api/curriculum-listes/login';
    private resourceByDivisionsIdUrl = SERVER_API_URL +  'api/curriculum-listes/divisions';

    constructor(private http: Http, private curriculumService: CurriculumService) {}

    create(curriculumList: CurriculumList): Observable<CurriculumList> {
        const copy = this.convert(curriculumList);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(curriculumList: CurriculumList): Observable<CurriculumList> {
        const copy = this.convert(curriculumList);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CurriculumList> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertCurriculum(jsonResponse);
            return jsonResponse;
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
        this.convertCurriculum(jsonResponse);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertCurriculum(jsonResponse: any) {
        if (jsonResponse.curriculumTimes != null) {
            for (let i = 0; i < jsonResponse.curriculumTimes.length; i++) {
                this.curriculumService.convertItemFromServer(jsonResponse.curriculumTimes[i]);
            }
        }
    }

    private convert(curriculumList: CurriculumList): CurriculumList {
        const copy: CurriculumList = Object.assign({}, curriculumList);
        copy.curriculums = Object.assign([], curriculumList.curriculums);
        if (copy.curriculums != null) {
            for (let i = 0; i < copy.curriculums.length; i++) {
                copy.curriculums[i] = this.curriculumService.convert(copy.curriculums[i]);
            }
        }
        return copy;
    }

}
