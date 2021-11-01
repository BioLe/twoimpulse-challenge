export interface PaginatedResponse<T> {
    readonly totalPages: number;
    readonly totalElements: number;
    readonly content: T[];
}

export default PaginatedResponse;