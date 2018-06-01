import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../app.constants';
import {createRequestOption, createRequestOptionWithDivisionsId, ResponseWrapper} from '../shared';
import {EntityService} from './entity.service';

export abstract class DivisionOwnerEntityService<EntityType> extends EntityService<EntityType> {

    protected resourceByCurrentLoginUrl: string;
    protected resourceByDivisionsIdUrl: string;
    protected resourceCalculateDefaultEntityFromSchool: string;

    constructor(http: Http,
                entityName: String) {
        super(http, entityName);
        this.resourceByCurrentLoginUrl = SERVER_API_URL + 'api/' + entityName + '/login';
        this.resourceByDivisionsIdUrl = SERVER_API_URL + 'api/' + entityName + '/division-owners';
        this.resourceCalculateDefaultEntityFromSchool = SERVER_API_URL + 'api/' + entityName + '/default';
    }

    findByDivisionOwner(ids: number[], req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourceByDivisionsIdUrl, createRequestOptionWithDivisionsId(ids, req))
            .map((res: Response) => this.convertResponses(res));
    }

    calculateDefaultEntity(schoolId: number): Observable<EntityType> {
        return this.http.get(`${this.resourceCalculateDefaultEntityFromSchool}/${schoolId}`) .map((res: Response) => {
                const jsonResponse = res.json();
                this.convertFromServer(jsonResponse);
                return jsonResponse;
            });
    }

    findByCurrentLogin(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceByCurrentLoginUrl, options)
            .map((res: Response) => this.convertResponses(res));
    }

}
