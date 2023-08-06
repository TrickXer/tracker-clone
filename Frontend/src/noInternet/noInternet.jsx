import './noInternet.css'
import React from 'react'

export default function NoInternet(props) {

    return (
        <>
            <div className="nonet-container">
                <div className="nonet-container-overlay" />
                <div className="nonet-found-box">
                    <div className="nonet-found-box-bg">
                        <div className="nonet-box-op" />
                    </div>

                    <div className="nonet-box-content">
                        <h1 className="nonet-title">no internet found</h1>
                        <hr className="nonet-title-divider" />
                        <p className="nonet-content">
                            We weren't able to connect to the internet to get an update that is required. please check your internet connection and try again.
                        </p>
                        <div className="nonet-button">
                            <button onClick={() => window.location.reload()} className="nonet-retry button-click-anim">retry</button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
