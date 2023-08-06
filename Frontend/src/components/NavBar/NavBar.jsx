/* eslint-disable react-hooks/exhaustive-deps */
import './NavBar.css'
import React from 'react'
import { NavLink, useNavigate } from 'react-router-dom'


export default function NavBar({ user, setUser, avatar, isLogin, setIsLogin }) {

    const tabs = [
        "home",
    ]

    const navigate = useNavigate()

    // const indicator = document.getElementsByClassName('tab-indicator')

    // const indicate = (item, id) => {
    //     console.log(indicator, item, id)
    //     indicator.style.left = item.offsetLeft + 'px'
    //     indicator.style.width = item.offsetWidth + 'px'
    // }

    return (
        <>
            <nav className='navbar'>
                <ul className='navbar-tabs'>
                    <div className='tab-indicator'></div>
                    {
                        tabs?.map((tab, id) => (
                            <li key={id} className='tab-box'>
                                <NavLink className='navbar-tab' key={id} to={`/${tab?.replace('home', '')}`}>{tab}</NavLink>
                            </li>
                        ))
                    }
                </ul>
                {
                    isLogin ? (
                        <div className="display-avatar" onClick={(e) => e.currentTarget.classList.toggle('active')} >
                            <img className='display-avatar-profile' src={avatar} alt={avatar} />
                            <div className="avatar-accordion">
                                <div className="list-item button-click-anim" onClick={ () => navigate('/profile') } >
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                                        <path d="M480-481q-66 0-108-42t-42-108q0-66 42-108t108-42q66 0 108 42t42 108q0 66-42 108t-108 42ZM160-160v-94q0-38 19-65t49-41q67-30 128.5-45T480-420q62 0 123 15.5t127.921 44.694q31.301 14.126 50.19 40.966Q800-292 800-254v94H160Zm60-60h520v-34q0-16-9.5-30.5T707-306q-64-31-117-42.5T480-360q-57 0-111 11.5T252-306q-14 7-23 21.5t-9 30.5v34Zm260-321q39 0 64.5-25.5T570-631q0-39-25.5-64.5T480-721q-39 0-64.5 25.5T390-631q0 39 25.5 64.5T480-541Zm0-90Zm0 411Z" />
                                    </svg>
                                    <p className="accordion-list">profile</p>
                                </div>
                                <div className="list-item button-click-anim" onClick={() => navigate(`/me/dashboard`) } >
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                                        <path d="M510-570v-270h330v270H510ZM120-450v-390h330v390H120Zm390 330v-390h330v390H510Zm-390 0v-270h330v270H120Zm60-390h210v-270H180v270Zm390 330h210v-270H570v270Zm0-450h210v-150H570v150ZM180-180h210v-150H180v150Zm210-330Zm180-120Zm0 180ZM390-330Z" />
                                    </svg>
                                    <p className="accordion-list">dashboard</p>
                                </div>
                                <hr className="list-item-divider" />
                                <div className="list-item button-click-anim" onClick={() => {
                                    setUser({})
                                    setIsLogin(false)
                                    navigate('/')
                                } } >
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                                        <path d="M645-327q-9-9-9-21.75t9-21.25l80-80H405q-12.75 0-21.375-8.675-8.625-8.676-8.625-21.5 0-12.825 8.625-21.325T405-510h318l-81-81q-8-8-8-20.447 0-12.448 9.214-21.5Q651.661-642 664.33-642q12.67 0 21.67 9l133 133q5 5 7 10.133 2 5.134 2 11Q828-473 826-468q-2 5-7 10L687-326q-8 8-20.5 8t-21.5-9ZM180-120q-24 0-42-18t-18-42v-600q0-24 18-42t42-18h261q12.75 0 21.375 8.675 8.625 8.676 8.625 21.5 0 12.825-8.625 21.325T441-780H180v600h261q12.75 0 21.375 8.675 8.625 8.676 8.625 21.5 0 12.825-8.625 21.325T441-120H180Z" />
                                    </svg>
                                    <p className="accordion-list">logout</p>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <NavLink to='/login' className='nav-button button-click-anim'>
                            <div className='highlight' />
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                                    <path d="M390-327q-9-9-9-21.75t9-21.25l80-80H150q-12.75 0-21.375-8.675-8.625-8.676-8.625-21.5 0-12.825 8.625-21.325T150-510h318l-81-81q-8-8-8-20.447 0-12.448 9.214-21.5Q396.661-642 409.33-642q12.67 0 21.67 9l133 133q5 5 7 10.133 2 5.134 2 11Q573-473 571-468q-2 5-7 10L432-326q-8 8-20.5 8t-21.5-9Zm129 207q-12.75 0-21.375-8.675-8.625-8.676-8.625-21.5 0-12.825 8.625-21.325T519-180h261v-600H519q-12.75 0-21.375-8.675-8.625-8.676-8.625-21.5 0-12.825 8.625-21.325T519-840h261q24 0 42 18t18 42v600q0 24-18 42t-42 18H519Z" />
                                </svg>
                            login
                        </NavLink>
                    )
                }
            </nav>
        </>
    )
}
