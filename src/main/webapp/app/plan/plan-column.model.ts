import {PlanCell} from './plan-cell.model';
import {Compartment} from './compartment/compartment-model';
import {CompartmentList} from './compartment/compartment-list-model';

export class PlanColumn {

    static isOverlapPlanCell(planCell1: PlanCell, planCell2: PlanCell): boolean {
        return !(planCell1.endHeightPercent < planCell2.startHeightPercent || planCell1.startHeightPercent > planCell2.endHeightPercent);
    }

    constructor(public date: Date,
                public planCells: PlanCell[] = []) {
    }

    calculatePosition() {
        this.sortByStartHeight();
        for (const planCell of this.planCells) {
            const neighbours = this.getNeighbours(planCell);
            const notFixedCells = neighbours.filter((cell) => !cell.fixedPosition);
            const fixedCells = neighbours.filter((cell) => cell.fixedPosition);
            const fixedAndValidCells = fixedCells.filter(
                (fixedCell) => notFixedCells.filter((notFixedCell) => PlanColumn.isOverlapPlanCell(notFixedCell, fixedCell)).length > 0);

            const compartmentList = new CompartmentList(fixedAndValidCells.map((cell) => new Compartment(cell.startWidthPercent, cell.endWidthPercent)));
            compartmentList.createNewFreeCompartments(notFixedCells.length);

            for (let i = 0; i < notFixedCells.length; i++) {
                notFixedCells[i].startWidthPercent = compartmentList.freeCompartments[i].startPosition;
                notFixedCells[i].endWidthPercent = compartmentList.freeCompartments[i].endPosition;
                notFixedCells[i].fixedPosition = true;
            }
        }
    }

    private getNeighbours(planCells: PlanCell): PlanCell[] {
        return this.planCells.filter((neighbour) => PlanColumn.isOverlapPlanCell(neighbour, planCells))
    }

    private sortByStartHeight() {
        this.planCells.sort((a: PlanCell, b: PlanCell) => {
            return a.startHeightPercent - b.startHeightPercent;
        });
    }

}
