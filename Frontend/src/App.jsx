// import logo from './logo.svg';
import './App.css';
import React, { Suspense, useEffect, useRef, useState } from 'react';
import NoInternet from './noInternet/noInternet';
import { Route, Routes, useLocation } from 'react-router-dom';
import NavBar from './components/NavBar/NavBar';
import defaultAvatar from './assets/default-avatar-profile-icon-vector-social-media-user-portrait-176256935.jpg'
import Alert from './components/Alert/Alert';
const LazyHome = React.lazy(() => import('./home/home'))
const LazyLogin = React.lazy(() => import('./login/login'))
const LazySignup = React.lazy(() => import('./signup/signup'))
const LazyProfile = React.lazy(() => import('./profile/profile'))
const LazyDashboard = React.lazy(() => import('./dashboard/Dashboard'))
const LazyLoginIssues = React.lazy(() => import('./loginIssues/loginIssues'))



function App() {

  const userInfo = JSON.parse(localStorage.getItem('user'))
  const loginInfo = JSON.parse(localStorage.getItem('is-login'))

  const newUser = {}

  const hasFetchedData = useRef(false)
  const hasFetchedRank = useRef(false)

  const [ranks, setRanks] = useState([])
  const [players, setPlayers] = useState([])
  const [isOnline, setIsOnline] = useState(navigator.onLine)
  const [user, setUser] = useState(userInfo === null || userInfo === undefined ? newUser : userInfo)
  const [isLogin, setIsLogin] = useState(loginInfo === null || loginInfo === undefined ? false : loginInfo)

  const handleStatus = () => {
    setIsOnline(navigator.onLine)
  }

  const location = useLocation().pathname

  useEffect(() => {
    window.addEventListener('online', handleStatus)
    window.addEventListener('offline', handleStatus)

    return () => {
      window.removeEventListener('online', handleStatus)
      window.removeEventListener('offline', handleStatus)
    }
  }, [isOnline, location])

  useEffect(() => {
    async function fetchData() {
      try {
        await fetch("https://valorant-api.com/v1/competitivetiers")
          .then(response => response.json())
          .then(data => setRanks(data?.data?.slice(-1)[0]['tiers']))
      } catch (e) {
        console.error('Error fetching api data', e)
      }
    }
    if (hasFetchedRank.current === false) {
      fetchData()
      hasFetchedRank.current = true
    }
  }, [])

  useEffect(() => {
    async function fetchData() {
      try {
        await fetch("http://localhost:8080/api/v1/game-stats/users?like=")
          .then(response => response.json())
          .then(data => setPlayers(data?.data))
      } catch (e) {
        console.error('Error fetching api data', e)
      }
    }
    if (hasFetchedData.current === false) {
      fetchData()
      hasFetchedData.current = true
    }
  }, [user])

  useEffect(() => {
    localStorage.setItem('is-login', JSON.stringify(isLogin))
  }, [isLogin])

  useEffect(() => {
    localStorage.setItem('user', JSON.stringify(user))
    hasFetchedData.current = false
  }, [user])

  const invalidNavBar = ['/login', '/sign-up']

  return (
    <>
      <Alert />
      {
        (!(invalidNavBar.includes(location)) && isOnline) &&
        <NavBar user={user} setUser={setUser} avatar={user?.image?.image === null ? defaultAvatar : `http://localhost:8080/api/v1/game-stats/users/image/${user?.image?.name}`} isLogin={isLogin} setIsLogin={setIsLogin} />
      }

      {
        isOnline ? (
          <Suspense fallback='Loading...'>
            <Routes>
              <Route path='/' element={
                <LazyHome ranks={ranks} user={user} setPlayers={setPlayers} players={players?.players} users={players?.users} defaultAvatar={defaultAvatar} setIsLogin={setIsLogin} />
              } />

              <Route path='/login' element={
                <LazyLogin user={user} setUser={setUser} setIsLogin={setIsLogin} />
              } />

              <Route path='/sign-up' element={
                <LazySignup setPlayers={setPlayers} user={user} setUser={setUser} />
              } />
              <Route path='/login-issues' element={
                <LazyLoginIssues defaultAvatar={defaultAvatar} />
              } />
              {isLogin && <Route path='/profile' element={
                <LazyProfile setPlayers={setPlayers} setIsLogin={setIsLogin} defaultAvatar={user?.image?.image === null ? defaultAvatar : `http://localhost:8080/api/v1/game-stats/users/image/${user?.image?.name}`} user={user} setUser={setUser} />
              } />}

              {
                players?.players?.map((player, id) => (
                  <Route key={id}
                    path={
                      `/${(user?.username === player?.in_game_name && user?.tagline === player?.in_game_tag) ? 'me' : `${player?.in_game_name} ${player?.in_game_tag}`}/dashboard`
                    }
                    element={
                      <LazyDashboard defaultAvatar={players?.users[id]?.image?.image === null ? defaultAvatar : `http://localhost:8080/api/v1/game-stats/users/image/${players?.users[id]?.image?.name}`} player={player} />
                    } />
                ))
              }
            </Routes>
          </Suspense>
        ) : (
          <NoInternet />
        )
      }
    </>
  );
}

export default App;
