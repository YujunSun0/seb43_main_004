import React from 'react'
import styled from 'styled-components'
// 폴더 인식이 안되서 잠깐 주석추가
type radio = {
  id: string
  name: string
  value: string
}

interface radioProps {
  legend: string
  radioArray: radio[]
  checkedValue: string
  error?: string
  onChange(e: React.ChangeEvent<HTMLInputElement>): void
}

const Radio = (props: radioProps) => {
  const { legend, radioArray, checkedValue, error, onChange } = props

  return (
    <RadioGroup>
      <Legend>{legend}</Legend>
      <RadioWrapper>
        {radioArray.map((i) => (
          <div key={i.id} className="radio-div">
            <input
              id={i.id}
              type="radio"
              name={i.name}
              value={i.value}
              checked={i.value === checkedValue}
              onChange={onChange}
            />
            <StyledLabel htmlFor={i.id}>{i.value}</StyledLabel>
          </div>
        ))}
      </RadioWrapper>

      {error && <ErrorMessage>{error}</ErrorMessage>}
    </RadioGroup>
  )
}

const RadioGroup = styled.div``

const RadioWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;

  input[type='radio'],
  input[type='radio']:checked + label {
    color: var(--color-point);
    accent-color: var(--color-point);
    font-weight: 600;
  }

  .radio-div {
    margin-top: 0.8rem;
  }
`

const Legend = styled.legend`
  display: inline-block;
  margin: 0 0 0.4rem 0.4rem;
  font-size: 1.4rem;
  font-weight: 700;
`

const StyledLabel = styled.label`
  font-size: 1.4rem;
`

const ErrorMessage = styled.p`
  margin: 0.4rem 0 0 0.4rem;
  color: var(--color-danger);
  font-size: 1.2rem;
`

export default Radio
