package com.example.flavi.model.data.datasource.actors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListActor(@SerialName("items") val items: List<ActorSearchDTO>)
