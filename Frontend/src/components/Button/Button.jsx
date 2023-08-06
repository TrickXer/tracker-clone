import { Link } from 'react-router-dom'
import styledButton from './Button.css'
import React from 'react'

export default function Button({ to, label, style = styledButton, rippleColor = "white" }) {

    const ripple = (e) => {
        let overlay = document.createElement('span')
        overlay.classList.add('button-overlay')

        let x = e.clientX - e.target.offsetParent.offsetLeft
        let y = e.clientY - e.target.offsetParent.offsetTop

        overlay.style.left = `${x}px`
        overlay.style.top = `${y}px`
        overlay.style.backgroundColor = rippleColor

        e.currentTarget.appendChild(overlay)

        setTimeout(() => {
            overlay.remove()
        }, 500);
    }

    return (
        <>
            <Link to={to} onClick={(e) => ripple(e)} className='styled-button' style={style} >
                <span className='styled-highlight' />
                {label}
            </Link>
        </>
    )
}
