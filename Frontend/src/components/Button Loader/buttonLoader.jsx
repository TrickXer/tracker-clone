import './buttonLoader.css'
import React from 'react'

export default function ButtonLoader({ width = '20px', height = '20px', color = '#FFF' }) {
    

    return (
        <>
            <div className="button-loader" style={{ width: width, height: height , borderTopColor: color}} />
        </>
    )
}
