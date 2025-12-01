package com.techcognics.procuremasster.data.remote

import com.techcognics.procuremasster.data.remote.dto.SubIndustryDto

data class SubIndustry(
    val id: Int,
    val industryId: Int,
    val subIndustryName: String
) : List<SubIndustryDto> {
    override fun contains(element: SubIndustryDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<SubIndustryDto>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): SubIndustryDto {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: SubIndustryDto): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<SubIndustryDto> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: SubIndustryDto): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<SubIndustryDto> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<SubIndustryDto> {
        TODO("Not yet implemented")
    }

    override fun subList(
        fromIndex: Int,
        toIndex: Int
    ): List<SubIndustryDto> {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")
}