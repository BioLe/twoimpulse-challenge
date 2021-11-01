import React, { useEffect, useState } from "react";
import {Card, CardContent, CardMedia, Typography, CardActionArea,
        Grid, Button, Chip} from '@mui/material';
import { Link } from 'react-router-dom';
import PaginatedResponse from "../../common/models/PaginatedResponse";
import Loan from "../loanModel";
import { DataGrid, GridValueFormatterParams, GridValueGetterParams } from '@mui/x-data-grid';
import LoanStateEnum from '../loanStateEnum';

interface RowFormat{
    id: number;
    inventoryId: number;
    title?: string;
    loanState: {};
    date: Date;
    libraryCardId: number;
    libraryId?: number;
}

function LoanTable(props: {loans: PaginatedResponse<Loan> | undefined, includeBookDetails: boolean, includeLibraryDetails: boolean}) {

    const PAGE_SIZE = 4;
    const [rows, setRows] = useState<RowFormat[]>();

    let columns = [
        { field: 'id', headerName: 'LoanID', width: 125 },
        { field: 'loanState', headerName: 'State', width: 140, renderCell: (params: any) => (
            <Chip label={params.value.label} color={params.value.color} />
        )},
        { field: 'date', headerName: 'ActionDate', width: 250, type: 'date'},
        ];

    if(props.includeBookDetails){
        columns.push(
            { field: 'inventoryId', headerName: 'InventoryID', width: 150},
            { field: 'title', headerName: 'BookTitle', width: 250, type: 'string'}
        )
    }

    if(props.includeLibraryDetails){
        
        columns.push(
            {field: 'libraryId', headerName: 'LibraryID', width: 150, type: 'string'},
            { field: 'libraryCardId', headerName: 'LibCardID', width: 150},
        );
    }


    useEffect( () => {

        let auxRows:RowFormat[] = [];
        props.loans?.content.map(loan => {
            auxRows.push(
                {
                    id: loan.loanId,
                    inventoryId: loan.inventory.inventoryId,
                    libraryCardId: loan.libraryCard.libraryCardId,
                    title: loan.inventory.book.title,
                    loanState: {
                        label:loan.loanState.state, 
                        color: (loan.loanState.state.toLowerCase() === LoanStateEnum.BORROWED.valueOf()) ? "warning" : "success"
                    },
                    date: loan.date,
                    libraryId: loan.libraryCard.library.l_ID
                }
            )
        })
        setRows(auxRows);

    }, [props])

    return (
        <div style={{ height: 350, width: '90%' }}>
        {rows && 
            
            
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={PAGE_SIZE}
                    disableSelectionOnClick
                />
            
                
        }
        </div>
    );
}

export default LoanTable;