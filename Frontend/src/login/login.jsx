import './login.css'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ButtonLoader from '../components/Button Loader/buttonLoader'
import { pop } from '../components/Alert/Alert'


export default function Login({ user, setUser, setIsLogin }) {

    const navigate = useNavigate()
    const [loading, setLoading] = useState(false)
    const [loginUser, setLoginUser] = useState({})

    const handleChange = (e) => {
        setLoginUser({ ...loginUser, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)

        try {
            setTimeout(() => {
                fetch("http://localhost:8080/api/v1/game-stats/users/login", {
                    method: 'post',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },

                    body: JSON.stringify(loginUser)
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.status === 202) {
                            setUser(data.data)
                            setLoading(false)
                            setIsLogin(true)
                            pop('login', 'successfully logged in')
                            navigate('/')
                        } else {
                            setIsLogin(false)
                        }
                    }).catch(err => console.log(err))
            }, 1 * 1000)
        }
        catch (error) {
            console.log(error)
        }
    }

    return (
        <>
            <div className='login-bg-image'>
                <div className='login-bg-darkness' />
                <div className="login-container">
                    <form className='login-form' onSubmit={handleSubmit} >
                        <div className="login-form-fields">
                            <input type="text" name="username" id="user" onChange={handleChange} required />
                            <div>
                                <label htmlFor='username'>username</label>
                            </div>

                            <input type="text" name="tagline" id="tag" onChange={handleChange} required />
                            <div>
                                <label htmlFor='tagline'>tagline</label>
                            </div>

                            <input type="password" name="password" id="password" onChange={handleChange} required />
                            <div>
                                <label htmlFor='password'>password</label>
                            </div>
                        </div>
                        <button type='submit' className='login-form-button button-click-anim' >
                            {
                                loading ? (
                                    <ButtonLoader width='17px' height='17px' />
                                ) : (
                                    "login"
                                )
                            }
                        </button>
                        <Link to='/sign-up'>create new account ?</Link>
                        <Link to='/login-issues'>sign-in issues ?</Link>
                    </form>
                </div>
            </div>
        </>
    )
}
