import './Dashboard.css'
import React from 'react'


function Display({ percentage, title, value }) {
    
    return (
        <>
            <div className="display-box">
                <div className="display-content">
                    <span className='display-title'>{title}</span>
                    <span className="display-value">{value}</span>
                    <span className="display-percentage">
                        <span className="percentage" style={{ width: `${percentage}%` }} />
                    </span>
                </div>
            </div>
        </>
    )
}

export default function Dashboard({ defaultAvatar, player }) {

    return (
        <>
            <div className="dashboard-container">
                <div className="dashboard-banner" />
                <div className="dashboard-body-container">
                    <div className="dashboard-body">
                        <div className="dashboard-display">
                            <img src={defaultAvatar} alt={defaultAvatar} className="dashboard-avatar" />
                            <div className="dashboard-name">{`${player.in_game_name} #${player.in_game_tag}`}</div>
                        </div>
                        <div className="dashboard-content">
                            <div className="dashboard-content-inner">
                                {
                                    Object?.keys(player)?.map((stats, id) => (
                                        (typeof(player[stats]) !== 'string' && stats !== 'valorant_id') &&
                                        <Display key={id} title={stats.replace("_", " ")} value={player[stats]} percentage={player[stats] > 100 ? 100 : player[stats]} />
                                    ))
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
