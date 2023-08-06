/* eslint-disable jsx-a11y/img-redundant-alt */
import './Alert.css'
import React from 'react'
import checkPng from './assets/Green-Check-Mark-PNG-Image.png'


const popContent = { title: null, message: null }

export const pop = (title, message, reirectTo) => {
    popContent.title = title
    popContent.message = message

    const alertBox = document.querySelector('.alert-box')
    alertBox.classList.add('pop')

    setTimeout(() => {
        alertBox.classList.remove('pop')
    }, 2 * 1000);
}

export default function Alert(props) {

    return (
        <>
            <div className="alert-box">
                <div className="pop-title">
                    <img className="tick-icon" src={checkPng} alt="Green-Check-Mark-PNG-Image.png" />
                    <span className="title">
                        {popContent.title}
                    </span>
                </div>
                <span className="content">{popContent.message}</span>
            </div>
        </>
    )
}
