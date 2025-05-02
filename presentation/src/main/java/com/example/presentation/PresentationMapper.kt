package com.example.presentation

interface PresentationMapper<DomainModel> {
    fun toDomain(): DomainModel
}