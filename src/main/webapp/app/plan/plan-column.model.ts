import {PlanCell} from './plan-cell.model';

export class PlanColumn {

    constructor(
        public date: Date,
        public planCells: PlanCell[] = []
    ) {
    }

}
