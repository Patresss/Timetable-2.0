import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../app.constants';
import {createRequestOption, createRequestOptionWithDivisionsId, ResponseWrapper} from '../shared';

export abstract class
EntityService<EntityType> {

    protected resourceUrl: string;

    constructor(protected http: Http,
                protected entityName: String) {
        this.resourceUrl = SERVER_API_URL + 'api/' + entityName;
    }

    create(entity: EntityType): Observable<EntityType> {
        const copy = this.convert(entity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(entity: EntityType): Observable<EntityType> {
        const copy = this.convert(entity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EntityType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertEntity(jsonResponse);
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

    convertEntity(jsonResponse: any) {
    }

    convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        this.convertEntity(jsonResponse);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    convert(entity: EntityType): EntityType {
        const copy: EntityType = Object.assign({}, entity);
        return copy;
    }

}
