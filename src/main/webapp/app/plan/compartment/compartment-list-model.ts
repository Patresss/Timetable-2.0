import {Compartment} from './compartment-model';

export class CompartmentList {

    private static epsilon = 1.1;

    public freeCompartments: Compartment[];

    constructor(public fixedPositionCompartments: Compartment[]) {
        this.freeCompartments = [];
        if (this.fixedPositionCompartments.length === 0) {
            this.freeCompartments.push(new Compartment(Compartment.defaultStart, Compartment.defaultEnd));
        } else {
            this.sortFixedPositionCompartmentsByStartPosition();
            this.calculateFreeCompartments();
        }
    }

    public createNewFreeCompartments(howManyCompartmentNeed: number) {
        if (this.freeCompartments.length < howManyCompartmentNeed && this.freeCompartments.length > 0) {
            let sum = 0.0;
            this.freeCompartments.forEach((compartment) => sum += compartment.length);
            const averageValueOfCompartment = sum / howManyCompartmentNeed;

            for (let i = this.freeCompartments.length - 1; i >= 0; i -= 1) {
                const compartmentToDivided = this.freeCompartments[i];
                const compartmentNumberToDiv = Math.round(compartmentToDivided.length / averageValueOfCompartment);
                const compartmentLength = compartmentToDivided.length / compartmentNumberToDiv;

                this.freeCompartments.splice(i, 1);

                for (let indexOfDiv = 0; indexOfDiv < compartmentNumberToDiv; indexOfDiv++) {
                    const startPosition = indexOfDiv * compartmentLength + compartmentToDivided.startPosition;
                    const endPosition = (indexOfDiv + 1) * compartmentLength + compartmentToDivided.startPosition;
                    const newCompartment = new Compartment(startPosition, endPosition);
                    this.freeCompartments.push(newCompartment);
                }
            }
        }
    }

    private sortFixedPositionCompartmentsByStartPosition() {
        this.fixedPositionCompartments.sort((a: Compartment, b: Compartment) => {
            const startDiff = a.startPosition - b.startPosition;
            return startDiff !== 0 ? startDiff : a.length - b.length;
        });
    }

    private calculateFreeCompartments() {
        const points = [];
        points.push(0.0);
        for (let i = 0; i < this.fixedPositionCompartments.length; i++) {
            points.push(this.fixedPositionCompartments[i].startPosition);
            points.push(this.fixedPositionCompartments[i].endPosition);
        }
        points.push(100.0);

        for (let i = 0; i < points.length; i += 2) {
            this.freeCompartments.push(new Compartment(points[i], points[i + 1]));
        }
        this.freeCompartments = this.freeCompartments.filter((compartment) => compartment.length > CompartmentList.epsilon)
    }

}
