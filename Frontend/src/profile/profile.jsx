import './profile.css'
import React, { useState } from 'react'
import ButtonLoader from '../components/Button Loader/buttonLoader'
import { pop } from '../components/Alert/Alert'
import { useNavigate } from 'react-router-dom'

export default function Profile({ defaultAvatar, user, setUser, setIsLogin, setPlayers }) {

    const [updateLoading, setUpdateLoading] = useState(false)
    const [deleteLoading, setdeleteLoading] = useState(false)
    const [checkDelete, setCheckDelete] = useState(false)
    const [updateUser, setUpdateUser] = useState(user)
    const [image, setImage] = useState(null)

    const navigate = useNavigate()

    const trigger = () => {
        let upload = document.querySelector('#avatar-upload')
        upload.click()
    }

    const fetchData = () => {
        try {
            fetch(`http://localhost:8080/api/v1/game-stats/users?like=`)
                .then(response => response.json())
                .then(data => setPlayers(data?.data))
        } catch (e) {
            console.error('Error fetching api data', e)
        }
    }

    const load = (e) => {
        let file = e?.target?.files[0]
        let src = URL?.createObjectURL(file)

        setImage(file)

        let avatar = document.querySelector('.profile-avatar')

        avatar.src = src
        avatar.alt = file?.name
    }

    const handleDelete = (e) => {
        setCheckDelete(true)
    }

    const handleConfirmSubmit = (e) => {
        const form = document.querySelector(".enter-password-box")
        let formData = new FormData(form)
        setdeleteLoading(true)

        fetch(`http://localhost:8080/api/v1/game-stats/users/delete-by-id/${user?.user_id}?password=${formData.get("password")}`, {
            method: 'delete'
        })
            .then(response => response.json())
            .then(data => {
                if (data?.status === 200) {
                    setUser({})
                    setIsLogin(false)
                    setdeleteLoading(false)
                    fetchData()
                    navigate('/')
                }
            })
        setCheckDelete(false)
    }

    const handleChange = (e) => {
        setUpdateUser({ ...updateUser, [e.target.name]: e.target.value })
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        setUpdateLoading(true)

        let formData = new FormData()

        formData.append("user", JSON.stringify(updateUser))
        formData.append("image", image)

        try {
            setTimeout(() => {
                fetch("http://localhost:8080/api/v1/game-stats/users/user/update", {
                    method: 'post',
                    body: formData,
                })
                    .then(response => response.json())
                    .then((data) => {
                    if (data?.status === 200) {
                        setUpdateLoading(false)
                        setUser(data?.data)

                        pop('update', 'user details updated successfully')
                        fetchData()
                    }
                    else {
                        setUpdateLoading(false)
                        console.log("update failed")
                    }
                }).catch((err) => {
                    setUpdateLoading(false)
                    console.log(err)
                })
            }, 1 * 1000);
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <>
            <div className='profile-container'>

                {
                    checkDelete &&
                    <div className="enter-password-container">
                        <form onSubmit={handleConfirmSubmit} className="enter-password-box">
                            <p>Enter password to confirm the "delete" action and Hit "Enter"</p>
                            <div className="input-field">
                                <div className="verify-by-password">
                                    <input type="password" name="password" id="password" required />
                                    <div>
                                        <label htmlFor='password'>password</label>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                }

                <div className="profile-box">
                    <form encType="multipart/form-data" method='post' className="profile-form" onSubmit={handleSubmit} >
                        <div className="avatar-upload">
                            <div className="edit-overloay" onClick={trigger} >
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                                    <path d="M471.6 21.7c-21.9-21.9-57.3-21.9-79.2 0L362.3 51.7l97.9 97.9 30.1-30.1c21.9-21.9 21.9-57.3 0-79.2L471.6 21.7zm-299.2 220c-6.1 6.1-10.8 13.6-13.5 21.9l-29.6 88.8c-2.9 8.6-.6 18.1 5.8 24.6s15.9 8.7 24.6 5.8l88.8-29.6c8.2-2.7 15.7-7.4 21.9-13.5L437.7 172.3 339.7 74.3 172.4 241.7zM96 64C43 64 0 107 0 160V416c0 53 43 96 96 96H352c53 0 96-43 96-96V320c0-17.7-14.3-32-32-32s-32 14.3-32 32v96c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H96z" />
                                </svg>
                                &nbsp;
                                <p>Edit</p>
                            </div>
                            <img className='profile-avatar' src={defaultAvatar} alt={defaultAvatar} />
                            <input type="file" name="image" id="avatar-upload" onChange={(e) => load(e)} accept='image/png, image/jpeg' />
                        </div>

                        <div className="profile-display">
                            <div className="input-field">
                                <input type="email" name="email" id="email" onChange={handleChange} defaultValue={user.email} />
                                <div>
                                    <label htmlFor='email'>Email</label>
                                </div>
                            </div>

                            <div className="input-field">
                                <input type="tel" name="phoneNo" id="phoneNo" onChange={handleChange} defaultValue={user.phoneNo} />
                                <div>
                                    <label htmlFor='phoneNo'>Phone No.</label>
                                </div>
                            </div>

                            {/* <div className="input-field">
                                <input type="date" name="dob" id="dob" onChange={handleChange} defaultValue={user.dob} />
                                <div id='dob-label'>
                                    <label htmlFor='dob'>D.O.B</label>
                                </div>
                            </div> */}
                        </div>
                        <button className='update-form-button button-click-anim' type="submit">
                            {
                                updateLoading ? (
                                    <ButtonLoader width='17px' height='17px' />
                                ) : (
                                    "update"
                                )
                            }
                        </button>
                    </form>
                    <button className='delete-form-button button-click-anim' onClick={ handleDelete }>
                        {
                            deleteLoading ? (
                                <ButtonLoader width='17px' height='17px' color='#FF4655' />
                            ) : (
                                "delete account"
                            )
                        }
                    </button>
                </div>
            </div>
        </>
    )
}
