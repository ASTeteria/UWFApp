import {FC, JSX} from 'react';

interface TableProps {
    headers: string[];
    rows: (string | JSX.Element)[][];
    onDelete?: (id: string) => void;
}

const Table: FC<TableProps> = ({ headers, rows, onDelete }) => {
    return (
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
            <tr>
                {headers.map(header => (
                    <th key={header} style={{ border: '1px solid #ccc', padding: '10px' }}>
                        {header}
                    </th>
                ))}
                {onDelete && <th style={{ border: '1px solid #ccc', padding: '10px' }}>Дії</th>}
            </tr>
            </thead>
            <tbody>
            {rows.map((row, index) => (
                <tr key={index}>
                    {row.map((cell, i) => (
                        <td key={i} style={{ border: '1px solid #ccc', padding: '10px' }}>
                            {cell}
                        </td>
                    ))}
                    {onDelete && (
                        <td style={{ border: '1px solid #ccc', padding: '10px' }}>
                            <button onClick={() => onDelete(row[1] as string)}>Видалити</button>
                        </td>
                    )}
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default Table;