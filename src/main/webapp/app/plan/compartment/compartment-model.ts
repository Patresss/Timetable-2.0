export class Compartment {

    public static defaultStart = 0.0;
    public static defaultEnd = 100.0;

    public length: number;

    constructor(public startPosition?: number,
                public endPosition?: number) {
        this.length = endPosition - startPosition
    }

}
