import './signup.css'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ButtonLoader from '../components/Button Loader/buttonLoader'
import { pop } from '../components/Alert/Alert'


export default function Signup({ user, setUser, setPlayers }) {

    const navigate = useNavigate()
    const [loading, setLoading] = useState(false)
    const [createUser, setCreateUser] = useState({})

    const fetchData = () => {
        try {
            fetch(`http://localhost:8080/api/v1/game-stats/users?like=`)
                .then(response => response.json())
                .then(data => setPlayers(data?.data))
        } catch (e) {
            console.error('Error fetching api data', e)
        }
    }

    const handleChange = (e) => {
        setCreateUser({ ...createUser, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        
        try {
            setTimeout(() => {
                fetch("http://localhost:8080/api/v1/game-stats/users/register", {
                    method: 'post',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },

                    body: JSON.stringify(createUser)
                })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 201) {
                        setLoading(false)
                        pop('registered', 'account registered successfully')
                        fetchData()
                        navigate('/login')
                    }
                })
            }, 5 * 1000)
        }
        catch (error) {
            console.log(error)
        }
    }

    return (
        <>
            <div className='signup-bg-image'>
                <div className='signup-bg-darkness' />
                <div className="signup-container">
                    <form className='signup-form' onSubmit={ handleSubmit } >
                        <div className="signup-form-fields" >
                            <input type="text" name="username" id="user" onChange={ handleChange } required />
                            <div>
                                <label htmlFor='username'>username</label>
                            </div>

                            <input type="text" name="tagline" id="tag" onChange={ handleChange } required />
                            <div>
                                <label htmlFor='tagline'>tagline</label>
                            </div>

                            <input type="email" name="email" id="email" onChange={handleChange} required />
                            <div>
                                <label htmlFor='email'>Email</label>
                            </div>

                            <input type="password" name="password" id="password" onChange={ handleChange } required />
                            <div>
                                <label htmlFor='password'>password</label>
                            </div>
                        </div>
                        <button type='submit' className='signup-form-button button-click-anim'>
                            {
                                loading ? (
                                    <ButtonLoader width='17px' height='17px' />
                                ) : (
                                    "register"
                                )
                            }
                        </button>
                        <Link to='/login'>already have an account ?</Link>
                    </form>
                </div>
            </div>
        </>
    )
}
