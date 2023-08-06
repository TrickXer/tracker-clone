import './home.css'
import React from 'react'
import { useNavigate } from 'react-router-dom'

export default function Home({ user, ranks, setPlayers, players, users, defaultAvatar }) {

    const navigate = useNavigate()

    const ripple = (e) => {
        let overlay = document.createElement('span')
        overlay.classList.add('button-overlay')

        let x = e.currentTarget.offsetWidth / 2
        let y = e.currentTarget.offsetHeight / 2

        overlay.style.left = `${x}px`
        overlay.style.top = `${y}px`

        e.currentTarget.appendChild(overlay)

        setTimeout(() => {
            overlay.remove()
        }, 250);
    }

    const showPlayers = () => {
        const search = document.querySelector('.search-bar')
        const form = document.querySelector('.search-player')

        if (search.value !== '') form.classList.add('show')
        else form.classList.remove('show')
    }

    const fetchPlayer = async () => {
        const search = document.querySelector('.search-bar')
        
        try {
            fetch(`http://localhost:8080/api/v1/game-stats/users?like=${search.value}`)
                .then(response => response.json())
                .then(data => setPlayers(data?.data))
        } catch (e) {
            console.error('Error fetching api data', e)
        }
    }

    return (
        <>
            <div className='bg-image'>
                <div className='bg-darkness' />
                <div className="home-container">
                    <div className='search-player' onChange={showPlayers} >
                        <input onChange={ fetchPlayer } title='search' type="search" className='search-bar' placeholder='Search' />
                        <button onClick={(e) => ripple(e)} name='search' type='button' className='search-button'>
                            <svg width='18' height='18' xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                                <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z" />
                            </svg>
                        </button>
                    </div>
                    <div className="show-players">
                        {
                            players?.map((player, id) => (
                                !(player?.in_game_name === user?.username && player?.in_game_tag === user.tagline) &&
                                <div key={id} className="player-div" onClick={() => navigate(`/${player.in_game_name} ${player.in_game_tag}/dashboard`)} >
                                    <img className='player-display-avatar' src={users[id]?.image?.image === null ? defaultAvatar : `http://localhost:8080/api/v1/game-stats/users/image/${users[id].image?.name}`} alt={users[id].image?.name} />
                                    <h3>{player?.in_game_name} #{player?.in_game_tag}</h3>
                                    {
                                        ranks?.map((rank, id) => (
                                            rank?.tierName?.toLowerCase() === player?.current_rank?.toLowerCase() &&
                                            <img title={player?.current_rank} width="48" height="48" key={id} src={rank?.smallIcon} alt={rank?.tierName} />
                                        ))
                                    }
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        </>
    )
}
