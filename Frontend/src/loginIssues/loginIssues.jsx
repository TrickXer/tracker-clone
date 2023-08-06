import { useNavigate } from 'react-router-dom'
import ButtonLoader from '../components/Button Loader/buttonLoader'
import './loginIssues.css'

import React, { useState } from 'react'
import { pop } from '../components/Alert/Alert'

export default function LoginIssues({ defaultAvatar }) {

    const [sendLoading, setSendLoading] = useState(false)
    const navigate = useNavigate()

    const handleSubmit = (e) => {
        e.preventDefault()
        const email = document.querySelector('#loginissue-email')

        setSendLoading(true)

        fetch(`http://localhost:8080/api/v1/game-stats/users/sign-in-issue/get-by-email?email=${email.value}`, {
            method: 'get'
        })
            .then(repsonse => repsonse.json())
            .then(data => {
                if (data.status === 302) {
                    setSendLoading(false)
                    pop('sent', 'mail sent successfully')
                    navigate('/login')
                }
            })
    }

    return (
        <>
            <div className="login-issues-container">
                <form className='login-issues' action="" onSubmit={handleSubmit}>
                    <img src={defaultAvatar} alt={defaultAvatar} />
                    <input type="email" name="email" id="loginissue-email" required />
                    <div>
                        <label htmlFor='email'>Enter email</label>
                    </div>
                    <button className='button-click-anim'>
                        {
                            sendLoading ? (
                                <ButtonLoader width='17px' height='17px' />
                            ) : (
                                "send email"
                            )
                        }
                    </button>
                </form>
            </div>
        </>
    )
}
